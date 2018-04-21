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
     [:option {:value "/examples/basic_arithmetic.fif"} "Basic Arithmetic"]
     [:option {:value "/examples/conditional_operators.fif"} "Conditional Operators"]
     [:option {:value "/examples/control_structures.fif"} "Control Structures"]
     [:option {:value "/examples/creating_function.fif"} "Defining Functions"]
     [:option {:value "/examples/loop_structures.fif"} "Loop Structures"]
     [:option {:value "/examples/defining_variables.fif"} "Defining Variables"]
     [:option {:value "/examples/collection_operators.fif"} "Collection Operators"]
     [:option {:value "/examples/word_referencing.fif"} "Word Referencing"]
     [:option {:value "/examples/functional_operators.fif"} "Functional Operators"]
     [:option {:value "/examples/the_collecter.fif"} "The Collecter"]
     [:option {:value "/examples/the_realizer.fif"} "The Realizer"]
     [:option {:value "/examples/macros.fif"} "Macros"]
     [:option {:value "/examples/query_example.fif"} "Query Example"]]))


