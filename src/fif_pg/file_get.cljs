(ns fif-pg.file-get
  (:require
   [cljs-http.client :as http]))


(defn get-text-file-chan [text-url]
  (http/get text-url
            {:content-type "text/plain"}))
