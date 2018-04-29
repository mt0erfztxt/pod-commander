(defproject pod-commander "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license
  {:name "Eclipse Public License"
   :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot [pod-commander.demo]
  :main pod-commander.demo
  :omit-source true
  :target-path "target/%s/"
  :uberjar-name "pod-commander-demo.jar"
  :dependencies
  [[com.google.guava/guava "25.0-jre"]
   [io.kubernetes/client-java "1.0.0-beta4"]
   [org.clojure/clojure "1.9.0"]])
