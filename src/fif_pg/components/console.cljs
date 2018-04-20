(ns fif-pg.components.console
  (:require
   [rum.core :as rum]
   [fif-pg.stack-machine :refer [eval-input-text!]]
   [fif-pg.console :as console]
   [fif-pg.utils :refer [create-uuid]]))

(rum/defc output-element
  < {:key-fn (fn [elem] (str (or (:key elem) (create-uuid))))}
  [elem]
  [:.div.output-element
   (condp = (:type elem)
    "header"
    [:pre.output-text.console-header (:text elem)]
    "info"
    [:pre.output-text.console-info (:text elem)]
    "stdout"
    [:pre.output-text.console-stdout (:text elem)]
    "stderr"
    [:pre.output-text.console-stderr (:text elem)]

    [:pre.output-text.console-stdout (:text elem)])])

(defn handle-keydown [app-state event]
  (let [key (-> event .-key)
        console-input-text (-> @app-state :console-input :text)]
    (when (and (= key "Enter") (> (count console-input-text) 0))
      (console/write-info! app-state console-input-text)
      (console/clear-input! app-state)
      (eval-input-text! app-state console-input-text))))


(rum/defcs
  c-console
  < rum/reactive
  [state app-state]
  (let [{:keys [console-output
                console-input]}
        (rum/react app-state)]
    [:.console-container
     (for [elem console-output]
       (output-element elem))
     [:.input-element
      [:.input-prompt ">"]
      [:input
       {:type "text"
        :value (-> console-input :text)
        :on-change #(swap! app-state assoc-in [:console-input :text] (-> % .-target .-value))
        :on-key-down #(handle-keydown app-state %)}]]]))


