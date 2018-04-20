(ns fif-pg.stack-machine
  (:require-macros [fif-pg.utils :refer [with-out-str-data-map]])
  (:require
   [fif.core :as fif :include-macros true]))

;;(binding [cljs.core/*print-newline* true]


(defn eval-input-text!
  [app-state input-text]
  (let [{:keys [stack-machine]} @app-state
        out
        (with-out-str-data-map
           (fif/with-stack stack-machine
             (fif/eval-string input-text)))

        sm (:result out)
        str-out (:str out)]
    (println "Output:" str-out)
    (swap! app-state update-in [:console-output] conj {:text str-out})
    (swap! app-state assoc :stack-machine sm)))

