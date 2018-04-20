(ns fif-pg.core
  (:require
   [fif.core :as fif :include-macros true]
   [rum.core :as rum]))

(enable-console-print!)

(.log js/console "Hello World!")


(.log js/console (clj->js (fif/reval 2 2 +)))


(println (fif/reval [2 0 do i loop] ? *inc <> map .s))


(def *app-state
  (atom
   {:stack-machine fif/*default-stack*}))
