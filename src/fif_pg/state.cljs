(ns fif-pg.state
  (:require
   [clojure.pprint]
   [clojure.string :as str]
   [fif.core :as fif :include-macros true]
   [fif.def :refer [wrap-function-with-arity
                    wrap-procedure-with-arity]]
   [fif.stack-machine.words :refer [set-global-word-defn]]
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
   {:fif-version "1.2.0"
    :stack-machine nil
    :console-input {:text "" :focused? true}
    :console-output [{:text "Fif Interactive Console" :type "header"}
                     {:text "Type 'help' and press enter for a help message."}]
    :codemirror-instance nil
    :dropdown-open? false}))


(defn main-stack-machine []
  (-> fif/*default-stack*
      (stack/set-system-error-handler system-error-handler)
      (stack/set-stack-error-handler stack-error-handler) 

      (set-global-word-defn
       'console/clear! (wrap-procedure-with-arity 0 (partial console/clear-output! *app-state))
       :doc "( -- ) Clear the output console."
       :group :fif-playground.console)

      (set-global-word-defn
       'console/header! (wrap-procedure-with-arity 1 (partial console/write-header! *app-state))
       :doc "( text -- ) Write header text to console."
       :group :fif-playground.console)

      (set-global-word-defn
       'console/info! (wrap-procedure-with-arity 1 (partial console/write-info! *app-state))
       :doc "( text -- ) Write info text to console."
       :group :fif-playground.console)

      (set-global-word-defn
       'console/out! (wrap-procedure-with-arity 1 (partial console/write-stdout! *app-state))
       :doc "( text -- ) Write stdout text to console."
       :group :fif-playground.console)

      (set-global-word-defn
       'console/error! (wrap-procedure-with-arity 1 (partial console/write-stderr! *app-state))
       :doc "( text -- ) Write stderr text to console."
       :group :fif-playground.console)))


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
