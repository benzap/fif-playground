(defproject fif-playground "0.2.0-SNAPSHOT"
  :description "fif - Online Playground"
  :url "http://github.com/benzap/fif-playground"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async "0.4.474"]
                 [fif "1.0.0"]
                 [rum "0.11.2"]
                 [org.roman01la/citrus "3.1.0"]
                 [cljs-http "0.1.45"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-ancient "0.6.15"]]

  ;;:hooks [leiningen.cljsbuild]
  :repositories [["clojars" {:sign-releases false}]]
  :cljsbuild {:builds {:dev
                       {:source-paths ["src"]
                        :compiler {:output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/fif-playground.js"
                                   :optimizations :none
                                   :main fif-pg.core
                                   :pretty-print true
                                   :source-map true}}
                       :min
                       {:source-paths ["src"]
                        :compiler {:output-to "resources/public/js/compiled/fif-playground.min.js"
                                   :optimizations :advanced
                                   :pretty-print false}}
                       :prod
                       {:source-paths ["src"]
                        :compiler {:output-to "dist/js/compiled/fif-playground.js"
                                   :optimizations :simple
                                   :pretty-print false}}}})
