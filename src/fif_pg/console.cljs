(ns fif-pg.console)


(defn insert-output!
  [app-state output-object]
  (swap! app-state update-in [:console-output] conj output-object))


(defn write-header! [app-state text]
  (insert-output! app-state {:text text :type "header"}))


(defn write-info! [app-state text]
  (insert-output! app-state {:text text :type "info"}))


(defn write-stdout! [app-state text]
  (insert-output! app-state {:text text :type "stdout"}))


(defn write-stderr! [app-state text]
  (insert-output! app-state {:text text :type "stderr"}))


(defn clear-input! [app-state]
  (swap! app-state assoc-in [:console-input :text] ""))
