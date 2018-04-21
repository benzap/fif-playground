(ns fif-pg.file-get)


(defn get-text-file-chan [text-url]
  (http/get text-url
            {:content-type "text/plain"}))
