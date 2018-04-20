(ns fif-pg.core
  (:require
   [fif.core :as fif :include-macros true]
   [rum.core :as rum]
   [fif.stack-machine :as stack]
   [fif.stack-machine.error-handling :refer [default-system-error-handler]]
   [fif-pg.console :as console]

   ;; Rum React Components
   [fif-pg.components.console :refer [c-console]]
   [fif-pg.components.editor :refer [c-editor]]
   [fif-pg.components.header :refer [c-header]]))

(enable-console-print!)


(def stack-machine
   (-> fif/*default-stack*))
       ;;(stack/set-system-error-handler default-system-error-handler)))


(def *app-state
  (atom
   {:fif-version "0.3.0-SNAPSHOT"
    :stack-machine stack-machine
    :console-input {:text "" :focused? true}
    :console-output [{:text "Fif Interactive Console" :type "header"}]
    :codemirror-instance nil
    :dropdown-open? false}))


(rum/defc main []
  [:.main-container
   (c-header *app-state)
   [:.split-container
    (c-console *app-state)
    (c-editor *app-state)]])


(rum/mount (main) (.getElementById js/document "app"))
