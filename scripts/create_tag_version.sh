#!/bin/bash

DATE=$(date +%Y%m%d)

COMMITS=$(git rev-list --count $(git describe --tags --abbrev=0)..HEAD 2>/dev/null || echo 0)

if [ "$COMMITS" = "0" ]; then
  COMMITS=$(git rev-list --count HEAD)
fi

VERSION_NAME="${DATE}.${COMMITS}"
echo "VERSION_NAME=$VERSION_NAME" >> $GITHUB_ENV
echo "TAG_NAME=v$VERSION_NAME" >> $GITHUB_ENV