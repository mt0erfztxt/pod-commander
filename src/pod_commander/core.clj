(ns pod-commander.core
  (:import
   [com.google.common.io ByteStreams]
   [io.kubernetes.client Configuration Exec]))

(defn- as-vector
  [v]
  (if (sequential? v) v [v]))

(defn exec
  ([client pod-name command] (exec client pod-name command nil))
  ([client pod-name command {:keys [container debug? stdin tty] :as options}]
   (-> client
       (.setDebugging (or debug? false))
       (Configuration/setDefaultApiClient))
   (let [cmd (into-array String (as-vector command))
         ns (or (:namespace options) "default")
         stdin (or stdin false)
         tty (or tty false)
         exec (Exec. client)
         _ (println
            (str
             "Executing " command
             " in pod '" pod-name (when container (str "/" container)) "'"
             " through API server " (.getBasePath client)))
         exec-process (.exec exec ns pod-name cmd container stdin tty)]
     (future
       (ByteStreams/copy System/in (.getOutputStream exec-process)))
     (deref
      (future
        (ByteStreams/copy (.getInputStream exec-process) System/out)))
     (.destroy exec-process)
     (let [exit-value (.exitValue exec-process)]
       (if (= exit-value 0)
         (println (str "  success (code: " exit-value ")"))
         (println (str "  failure (code: " exit-value ")")))
       (System/exit exit-value)))))
