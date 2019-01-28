(ns fif-pg.components.header
  (:require
   [rum.core :as rum]))


(rum/defcs c-header
  [app-state]
  [:.header-container
   "fif - 1.0.4"
   [:a {:href "http://github.com/benzap/fif"} "Github Page"]])
