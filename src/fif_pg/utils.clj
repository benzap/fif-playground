(ns fif-pg.utils)

(defmacro with-out-str-data-map
  [& body]
  `(let [sb# (js/goog.string.StringBuffer.)]
     (binding [cljs.core/*print-newline* true
               cljs.core/*print-fn* (fn [x#] (.append sb# x#))]
       (let [r# ~@body]
         {:result r#
          :str    (str sb#)}))))
