(ns fif-pg.components.dropdown
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [cljs.core.async :refer [<!]]
   [cljs-http.client :as http]

   [rum.core :as rum]
   [fif-pg.file-get :refer [get-text-file-chan]]))


(defn set-editor-example! [app-state text-url]
  (if (> (count text-url) 0)
    (go (let [text-response (<! (http/get text-url {:content-type "text/plain"}))
              content (:body text-response)
              codemirror-instance (:codemirror-instance @app-state)]
          (when content
            (.setValue codemirror-instance content))))
    (.error js/console "No Url Provided:" text-url)))


(rum/defcs c-dropdown
  <
  rum/reactive
  [state app-state]
  (let [{:keys [dropdown-open?]} (rum/react app-state)]
    [:select.editor-dropdown {:on-change #(set-editor-example! app-state (-> % .-target .-value))}
     [:option {:value ""} ""]
     [:option {:value "/examples/query_example.fif"} "Query Example"]]))


