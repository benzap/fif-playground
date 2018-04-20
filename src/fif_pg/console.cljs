(ns fif-pg.console
  (:require [fif-pg.utils :refer [create-uuid]]))


(defn insert-output!
  [app-state output-object]
  (swap! app-state update-in [:console-output] conj output-object))


(defn write-header! [app-state text]
  (insert-output! app-state {:text text :type "header" :key (create-uuid)}))


(defn write-info! [app-state text]
  (insert-output! app-state {:text text :type "info" :key (create-uuid)}))


(defn write-stdout! [app-state text]
  (insert-output! app-state {:text text :type "stdout" :key (create-uuid)}))


(defn write-stderr! [app-state text]
  (insert-output! app-state {:text text :type "stderr" :key (create-uuid)}))


(defn clear-input! [app-state]
  (swap! app-state assoc-in [:console-input :text] ""))
