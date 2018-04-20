(ns fif-pg.core
  (:require
   [fif.core :as fif :include-macros true]
   [rum.core :as rum]

   ;; Rum React Components
   [fif-pg.components.console :refer [c-console]]
   [fif-pg.components.editor :refer [c-editor]]
   [fif-pg.components.header :refer [c-header]]))

(enable-console-print!)

(.log js/console "Hello World!")


(.log js/console (clj->js (fif/reval 2 2 +)))


(println (fif/reval [2 0 do i loop] ? *inc <> map .s))



(def *app-state
  (atom
   {:fif-version "0.3.0-SNAPSHOT"
    :stack-machine fif/*default-stack*
    :console-input []
    :console-output []}))


(rum/defc main []
  [:.main-container
   (c-header *app-state)
   [:.split-container
    (c-console *app-state)
    (c-editor *app-state)]])


(rum/mount (main) (.getElementById js/document "app"))
