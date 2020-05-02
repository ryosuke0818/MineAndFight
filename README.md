# MineAndFight

## ブレストドキュメント
https://docs.google.com/document/d/1d3uQ8OxaxYZDeLQ6yz--hRBu01LgBc8lJ2UauCsBGKI/edit#


## プロジェクトのクローン
git clone https://github.com/yokmama/MineAndFight.git

## gitに変更を追加する
git add .

## gitに変更をコミットする
git commit

### gitのコミットのエディタを vscodeにする
git config --global core.editor 'code --wait'

### gitの変更をサーバーにPushする
git push

### サーバーから新しい変更を取得する
git pull

### リモートデバッグ用　start.shの書き方
#!/bin/bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar spigot-1.15.2.jar nogui
