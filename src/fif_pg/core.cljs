(ns fif-pg.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [fif.core :as fif :include-macros true]
   [rum.core :as rum]
   [cljs.core.async :refer [<!]]
   [cljs-http.client :as http]
   [fif-pg.console :as console]
   [fif-pg.state :refer [*app-state]]

   ;; Rum React Components
   [fif-pg.components.console :refer [c-console]]
   [fif-pg.components.editor :refer [c-editor]]
   [fif-pg.components.header :refer [c-header]]))


(enable-console-print!)


(rum/defc main []
  [:.main-container
   (c-header *app-state)
   [:.split-container
    (c-console *app-state)
    (c-editor *app-state)]])


(rum/mount (main) (.getElementById js/document "app"))
