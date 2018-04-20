(ns fif-pg.components.console
  (:require
   [rum.core :as rum]
   [fif-pg.stack-machine :refer [eval-input-text!]]))


(rum/defc output-element
  [elem]
  [:.div.output-element
   [:pre.output-text (:text elem)]])


(defn handle-keydown [app-state event]
  (let [key (-> event .-key)
        console-input-text (-> @app-state :console-input :text)]
    (when (and (= key "Enter") (> (count console-input-text) 0))
      (swap! app-state update-in [:console-output] conj {:text console-input-text})
      (swap! app-state assoc-in [:console-input :text] "")
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


