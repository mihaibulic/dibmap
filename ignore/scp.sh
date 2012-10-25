#!/bin/bash

scp -i $HOME/.ec2/dibmap1.pem "$1" ec2-user@ec2-184-72-242-128.compute-1.amazonaws.com:"$2"

