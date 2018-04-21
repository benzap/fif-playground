(ns fif-pg.state
  (:require
   [fif.core :as fif :include-macros true]
   [fif.stack-machine.error-handling :as error-handling]
   [fif.stack-machine :as stack]
   [fif-pg.console :as console]))


(declare reset-stack-machine!
         error-handler)


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
      (stack/set-system-error-handler error-handler)))


(defn reset-stack-machine! []
  (swap! *app-state assoc :stack-machine (main-stack-machine)))


(defn error-handler [sm ex]
  (let [errmsg (str "System Error")
        errobj (error-handling/system-error sm ex errmsg)]
    (console/write-stderr! *app-state (str ex))
    (reset-stack-machine!)
    (-> sm (error-handling/set-error errobj))))


(reset-stack-machine!)
