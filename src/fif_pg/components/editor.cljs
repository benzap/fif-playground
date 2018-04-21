(ns fif-pg.components.editor
  (:require
   [rum.core :as rum]
   [fif-pg.stack-machine :refer [eval-input-text!]]
   [fif-pg.console :as console]

   [fif-pg.components.dropdown :refer [c-dropdown]]))


(def codemirror-config
  #js {:mode "clojure"
       :theme "mdn-like" 
       :lineNumbers true
       :autofocus true
       :cursorScrollMargin 50
       :lineNumberFormatter
       (fn [i] (if-not (= i 5) (str i) "fif"))})


(defn handle-run-script
  [app-state]
  (let [{:keys [codemirror-instance]} @app-state
        text-input (.getValue codemirror-instance)]
    (console/write-info! app-state "Evaluating Script...")
    (eval-input-text! app-state text-input)))


(defn editor-mixin []
  {:did-mount
   (fn [state]
     (let [app-state           (-> state :rum/args first)
           comp                (:rum/react-component state)
           dom-node            (js/ReactDOM.findDOMNode comp)
           elem-container      (.querySelector dom-node ".codemirror-container")
           codemirror-instance (js/CodeMirror elem-container codemirror-config)]
       (swap! app-state assoc :codemirror-instance codemirror-instance)
       state))})


(rum/defcs
  c-editor
  <
  rum/reactive
  (editor-mixin)
  [state app-state]
  (let [{:keys []} (rum/react app-state)]
    [:.editor-container
     [:.editor-menu-bar
      [:.editor-menu-group
       [:.editor-menu-label "Examples:"]
       (c-dropdown app-state)]
      [:div.editor-button {:on-click #(handle-run-script app-state)} "Run Script"]]
     [:.codemirror-container]]))

