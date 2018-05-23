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
    [:span.output-text.console-header (:text elem)]
    "info"
    [:span.output-text.console-info (:text elem)]
    "stdout"
    [:span.output-text.console-stdout (:text elem)]
    "stderr"
    [:span.output-text.console-stderr (:text elem)]

    [:span.output-text.console-stdout (:text elem)])])


(defn handle-keydown [app-state event]
  (let [key (-> event .-key)
        console-input-text (-> @app-state :console-input :text)]
    (when (and (= key "Enter") (> (count console-input-text) 0))
      (console/write-info! app-state console-input-text)
      (console/clear-input! app-state)
      (eval-input-text! app-state console-input-text))))


(defn handle-autoscroll []
  {:did-mount
   (fn [state]
     (let [elem-console-container (.querySelector js/document ".console-container")]
       (.addEventListener
        elem-console-container "click"
        (fn [e]
          (let [elem (.querySelector js/document ".input-element > input")]
            (.focus elem))))
       state))
   :did-update
   (fn [state]
     (let [elem-console-container (.querySelector js/document ".console-container")]
       (aset elem-console-container "scrollTop" (.-MAX_SAFE_INTEGER js/Number))
       state))})



(rum/defcs
  c-console
  <
  rum/reactive
  (handle-autoscroll)
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


