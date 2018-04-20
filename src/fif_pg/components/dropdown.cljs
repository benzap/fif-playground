(ns fif-pg.components.dropdown
  (:require
   [rum.core :as rum]))


(defn toggle-dropdown! [app-state]
  (swap! app-state update :dropdown-open? not))


(rum/defcs c-dropdown
  <
  rum/reactive
  [state app-state]
  (let [{:keys [dropdown-open?]} (rum/react app-state)]
    [:.dropdown-container.editor-button
     [:.dropdown-button
      {:on-click #(toggle-dropdown! app-state)}
      [:.button-label "Examples ▼"]]
     (when dropdown-open?
       [:.dropdown-menu-container
        "Menu Listing"])]))
       
   
