(ns fif-pg.stack-machine
  (:require-macros [fif-pg.utils :refer [with-out-str-data-map]])
  (:require
   [clojure.string :as str]
   [fif.core :as fif :include-macros true]
   [fif-pg.state :refer [*app-state]]))


(defn split-text-console-output [s]
  (->> (str/split-lines s)
       (map (fn [x] {:text (str/replace x #" " "\u00a0")}))))


(defn eval-input-text!
  [app-state input-text]
  (let [{:keys [stack-machine]} @app-state
        out
        (with-out-str-data-map
           (fif/with-stack stack-machine
             (fif/eval-string input-text)))

        sm (:result out)
        console-out (split-text-console-output (:str out))]
    (swap! app-state update-in [:console-output] #(vec (concat %1 %2)) console-out)
    (swap! app-state assoc :stack-machine sm)))
