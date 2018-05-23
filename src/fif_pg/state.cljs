(ns fif-pg.state
  (:require
   [clojure.pprint]
   [clojure.string :as str]
   [fif.core :as fif :include-macros true]
   [fif.stack-machine.error-handling :as error-handling]
   [fif.stack-machine :as stack]
   [fif-pg.console :as console]))


(declare reset-stack-machine!
         system-error-handler
         stack-error-handler)


(defn pprint-str [o]
  (with-out-str (clojure.pprint/pprint o)))


(def *app-state
  (atom
   {:fif-version "0.3.0-SNAPSHOT"
    :stack-machine nil
    :console-input {:text "" :focused? true}
    :console-output [{:text "Fif Interactive Console" :type "header"}]
    :codemirror-instance nil
    :dropdown-open? false}))


(defn main-stack-machine []
  (-> fif/*default-stack*
      (stack/set-system-error-handler system-error-handler)
      (stack/set-stack-error-handler stack-error-handler)
      ))


(defn reset-stack-machine! []
  (swap! *app-state assoc :stack-machine (main-stack-machine)))



(defn stack-error-handler [sm errobj]
  (doseq [s (-> errobj pprint-str str/split-lines)]
    (console/write-stderr! *app-state s))
  (reset-stack-machine!)
  (error-handling/set-error sm errobj))


(defn system-error-handler [sm ex]
  (let [errmsg (str "System Error")
        errobj (error-handling/system-error sm ex errmsg)]
    (console/write-stderr! *app-state (str "ERROR: " ex))
    (doseq [s (-> errobj pprint-str str/split-lines)]
      (console/write-stderr! *app-state s))
    (reset-stack-machine!)
    (error-handling/set-error sm errobj)))


(reset-stack-machine!)
