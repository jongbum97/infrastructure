cd /home/ubuntu/test
git fetch

if [ -n "$(git diff origin/master)" ]; then

	echo "Changes were detected."
	echo "Starting script..."

	git pull origin master
	sudo docker-compose down --rmi all
	sudo docker-compose up -d --build
	sudo docker ps -a
	sudo docker images

	echo "Finished"

else
	  echo "No changes were detected."
fi
