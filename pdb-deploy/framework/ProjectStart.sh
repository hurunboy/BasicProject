#!/usr/bash
#set -x
set -e

moduleName=""
case "$1" in
    admin)
    moduleName=admin
    ;;
    api)
    moduleName=api
    ;;
    task)
    moduleName=task
    ;;
    wallet)
    moduleName=wallet
    ;;
    *)
    echo "Usage: $0 (admin | api | task | wallet)"
    exit 1
esac

ProjectName=estate-$moduleName

exec 8<>/home/www/deploy-sh/$ProjectName.lock
flock -n 8 ||exit 1


#PASSWD=8df2fe016614f2a90dee6190ce076976
PASSWD=123456


########################################################################################
SourceGitPath=/home/www/source/estate
ProjectPackgPath=/home/www/source/estate/estate-deploy/target/web-deploy.zip
Devn=dev
Host=192.168.1.221

########################################################################################
HostProt=22
LockFile=~/sbin/$ProjectName.lock

########################################################################################

###查询锁文件，并建锁
#if [ -f "$LockFile" ]; then
#    echo 'READY RUNNING!!'
#    exit
#fi
#echo $$ > $LockFile


cd $SourceGitPath
git checkout dev
git pull
mvn clean package -P"$moduleName" -Denv=$Devn 


sshpass -p "$PASSWD" scp -P "$HostProt" "$ProjectPackgPath"  www@"$Host":/home/www/htdocs/$ProjectName/


sshpass -p "$PASSWD" ssh -p "$HostProt" www@"$Host" "export ProjectName=$ProjectName;/bin/bash /home/www/sbin/ProjectRestart.sh"
# StaticUpload

###删锁
rm -rf /home/www/deploy-sh/$ProjectName.lock
