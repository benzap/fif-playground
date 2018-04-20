(ns fif-pg.components.header
  (:require
   [rum.core :as rum]))


(rum/defcs c-header
  [app-state]
  [:.header-container
   "Header"])

