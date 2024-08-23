'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#username-form');
var messageForm = document.querySelector('#message-form');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#message-area');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function createUserUiItem(name) {
    var rootElement = document.createElement('li');
    rootElement.textContent = name;
    rootElement.classList.add('user-item');
    rootElement.style.backgroundColor = getAvatarColor(name);
    return rootElement;
}

function createMessageUiItem(message) {
    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(message.user[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style.backgroundColor = getAvatarColor(message.user);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    usernameElement.classList.add('username');
    var usernameText = document.createTextNode(message.user);
    usernameElement.appendChild(usernameText);
    var dateElement = document.createElement('span');
    dateElement.classList.add('date');
    dateElement.textContent = message.date;
    usernameElement.appendChild(dateElement);

    messageElement.appendChild(usernameElement);

    return messageElement;
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    // Subscribe to the Reply Topic
    stompClient.subscribe('/user/' + username + '/topic/reply', onMessageReceived);
    // Tell your username to the server
    stompClient.send('/app/chat.addUser',
        {},
        JSON.stringify({user: username, type: 'JOIN'})
    )
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            user: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.user + ' joined!';

    } else if (message.type === 'USERS') {
        var usersArea = document.getElementById('users-area');
        usersArea.innerHTML = '';
        var users = message.users;
        if (usersArea && Array.isArray(users)) {
            for (var user of users) {
                var userElement = createUserUiItem(user);
                usersArea.appendChild(userElement);
            }
        }
        return;

    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.user + ' left!';

    } else {
        messageElement = createMessageUiItem(message);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(username) {
    var hash = 0;
    for (var i = 0; i < username.length; i++) {
        hash = 31 * hash + username.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)