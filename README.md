# pod-commander

A Clojure library designed to ... well, that part is up to you.

## Usage

FIXME

## Demo
### Requirements

`minikube` and `kubectl` must be installed and available on `PATH`.

### Notes
* During setup `minkube` cluster would be reseted and all you data would be lost! So, you maybe need to backup `minikube`'s VM first.

### Setup and run

1. In new shell (let name it shell-1) run `./demo/setup`.
2. After setup is done run `./demo/build_and_deploy` in shell-1, wait til PODs status changed to `Running` and press `Ctrl-C`.
3. Run `kubectl --namespace=demo attach executor --container=c02` in shell-1 to attach to container of `executor` pod and check that printed listing (updated each 5 seconds) of `/tmp` directory doesn't contain `hello.txt` entry.
4. In new shell (let name it shell-2) run `kubectl --namespace=demo exec commander -it /bin/bash` to get shell into `commander` POD's first container, run `/command.sh` and wait til command completed.
5. Switch to shell-1 and check that printed listing (updated each 5 seconds) of `/tmp` directory now contains `hello.txt` entry.

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
