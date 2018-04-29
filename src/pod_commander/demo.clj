(ns pod-commander.demo
  (:gen-class)
  (:require
   [pod-commander.core :as pod-commander]
   [pod-commander.client :as client]))

(defn -main [pod-name & command]
  (let [client
        (client/from-certificate-and-key
         "/demouser.crt"
         "/demouser.key")]
    (pod-commander/exec
     client
     pod-name
     command
     {:container "c02"   ; must be set when pod has more than one container
      :debug? false      ; set to true for debug info to be printed
      :namespace "demo"})))
