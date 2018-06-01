(ns pinpoint.core
  (:require
   [clojure.repl]
   [com.stuartsierra.component :as component]))

(defonce patches
  {#'cast {:original cast
           :replacement (fn [^Class c x]
                          (try
                            (. c (cast x))
                            (catch ClassCastException e
                              (doto (ex-info (str (pr-str x)
                                                  " (" (-> x class pr-str) ") cannot be cast to "
                                                  (pr-str c))
                                             {:faulty-value x
                                              :attempted-to-cast-to c
                                              :e e})
                                (.setStackTrace (.getStackTrace e))
                                (throw)))))
           :expected-source "(defn cast\n  \"Throws a ClassCastException if x is not a c, else returns x.\"\n  {:added \"1.0\"\n   :static true}\n  [^Class c x] \n  (. c (cast x)))"}})

(defn patch-all! []
  (doseq [[target-var {:keys [replacement expected-source]}] patches]
    (assert (= expected-source
               (-> target-var meta :name clojure.repl/source-fn)))
    (alter-var-root target-var (constantly replacement))))

(defn unpatch-all! []
  (doseq [[target-var {:keys [original]}] patches]
    (alter-var-root target-var (constantly original))))

(defrecord Pinpoint []
  component/Lifecycle
  (start [_]
    (patch-all!))
  (stop [_]
    (unpatch-all!)))
