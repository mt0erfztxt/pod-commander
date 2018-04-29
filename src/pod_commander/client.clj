(ns pod-commander.client
  (:require
   [clojure.java.io :as java-io])
  (:import
   [io.kubernetes.client.util ClientBuilder]
   [io.kubernetes.client.util.credentials ClientCertificateAuthentication]
   [java.nio.file Files Paths]))

(def ca-crt-filepath
  "The place where CA stored in POD."
  "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt")

(defn- filepath->bytes
  [filepath]
  (let [file (java-io/file filepath)]
    (when-not (.exists file)
      (throw
       (IllegalArgumentException. (str "No file found at path '" filepath "'"))))
    (-> file
        (.toURI)
        (Paths/get)
        (Files/readAllBytes))))

(defn- build-api-server-base-path
  ([] (build-api-server-base-path nil nil))
  ([host] (build-api-server-base-path host nil))
  ([host port]
   (let [host (or host (System/getenv "KUBERNETES_SERVICE_HOST"))
         port (or port (System/getenv "KUBERNETES_SERVICE_PORT"))]
     (str "https://" host ":" port))))

(defn from-certificate-and-key
  [client-crt-filepath client-key-filepath]
  (let [auth
        (ClientCertificateAuthentication.
         (filepath->bytes client-crt-filepath)
         (filepath->bytes client-key-filepath))
        client-builder
        (doto (ClientBuilder.)
          (.setBasePath (build-api-server-base-path))
          (.setCertificateAuthority (filepath->bytes ca-crt-filepath))
          (.setAuthentication auth))]
    (.build client-builder)))
