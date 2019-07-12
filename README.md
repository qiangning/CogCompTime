# Description
This is the backend system of the online demo [here](http://groupspaceuiuc.com/temporal/).
# Prerequisites
- This package was tested on Ubuntu Mate 16.04.
- Have [maven](https://maven.apache.org/install.html) installed in your system
- Have [Gurobi](http://www.gurobi.com/downloads/gurobi-optimizer) installed in your system and have the environment variables `GUROBI_HOME` and `GRB_LICENSE_FILE` setup in your path, as required by Gurobi. Gurobi would typically require adding the following lines to your .bashrc or .bash_profile, but please refer to Gurobi's installment instructions for details.
  ```
  export GUROBI_HOME=/opt/gurobi652/linux64 # "652" is the version number 6.5.2; we also tested with 8.1.0
  export PATH=$PATH:$GUROBI_HOME/bin
  export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$GUROBI_HOME/lib
  export GRB_LICENSE_FILE=$GUROBI_HOME/gurobi.lic
  ```
  **Note that if you can also run it without Gurobi, so that the transitivity constraints are not enforced during inference (which may be problematic in some applications)**

# Compile
All the following commands should be run from the root dir of the project, i.e., `CogCompTime/`.

```
git clone git@github.com:qiangning/CogCompTime.git
cd CogCompTime
sh scripts/install.sh # to install gurobi.jar into the local .m2 repo
mvn compile
```

# Resources
Please also download `WordNet-3.0` and `TemProb.txt` from my another repo [TemProb-NAACL18](https://github.com/qiangning/TemProb-NAACL18/tree/master/data) and put them into `CogCompTime/data/` before you move forward.

# Example Usage
If no error messages pop up, you're can move forward by
```
sh scripts/example.sh
```

Again, if no errors are encountered, you should be able to see the output in `data/output/GeorgeLowe-long.html` (open it via any internet browser and you will see the *temporal graph* you see in our online demo) and `data/output/GeorgeLowe-long.txt` (the *timeline* you see in our online demo). Compare them with the reference output `data/output/REF-GeorgeLowe-long` to see if everything is the same.

# Trouble shooting
- The first time you run the code, one component of CogCompTime will automatically download some resource files from CogCompGroup servers (specifically, that component is `illinois-nlp-pipeline`. Those resource files will be downloaded and saved into your `$HOME/.cogcomp-datastore`. If you have trouble downloading them, you can try this back-up [link (300MB)](http://qning2.web.engr.illinois.edu/misc/cogcomp-datestore.tar.gz) and unzip it into `$HOME/.cogcomp-datastore`.
- There's a known issue in `illinois-nlp-pipeline` described [here](https://github.com/CogComp/cogcomp-nlp/issues/708). That is, when you use `illinois-nlp-pipeline` to process semantic role labeling (SRL), it's possible that it requires more than 32GB of memory. CogCompTime indeed relies on that SRL component. So if you do see this issue, you can try CogCompTime on a machine with more than 32GB in memory.

# Citation
Please kindly cite the following paper: *Qiang Ning, Ben Zhou, Zhili Feng, Haoruo Peng and Dan Roth, CogCompTime: A Tool for Understanding Time in Natural Language EMNLP (Demo Track) (2018)* ([pdf](http://cogcomp.org/papers/NZFPR18.pdf))

```
@inproceedings{NZFPR18,
    author = {Qiang Ning and Ben Zhou and Zhili Feng and Haoruo Peng and Dan Roth},
    title = {CogCompTime: A Tool for Understanding Time in Natural Language},
    booktitle = {EMNLP (Demo Track)},
    month = {11},
    year = {2018},
    address = {Brussels, Belgium},
    publisher = {Association for Computational Linguistics},
    url = "http://cogcomp.org/papers/NZFPR18.pdf",
}
```
