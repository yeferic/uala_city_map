name: Block PRs to main that don't come from develop

on:
pull_request:
branches:
- main

jobs:
check-pr-source:
runs-on: ubuntu-latest
steps:
- name: Fail PR if source is not develop
if: github.head_ref != 'develop'
run: |
echo "❌ Only pull requests from 'develop' to 'main' are allowed."
exit 1