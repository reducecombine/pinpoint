(defproject pinpoint "0.1.0-SNAPSHOT"
  :description "Decorates selected clojure.core stacktraces with culprit values."
  :url "https://github.com/vemv/pinpoint"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-opts ["-Dclojure.compiler.direct-linking=false"]
  :dependencies [[com.stuartsierra/component "0.3.2"]
                 [org.clojure/clojure "1.7.0"]])
