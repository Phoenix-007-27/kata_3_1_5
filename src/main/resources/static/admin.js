"use strict";

const url = "http://localhost:8080/api/users"

async function getAdminPage() {
    let page = await fetch(url);
    if (page.ok) {
        let listAllUser = await page.json();

        loadTableData(listAllUser);
    } else {
        alert(`Error, ${page.status}`)
    }
}

const pills = document.querySelectorAll('.pill');
const pillsContent = document.querySelectorAll('.pillContent');
pills.forEach((clickedPill) => {
    clickedPill.addEventListener('click', async () => {
        pills.forEach((pill) => {
            pill.classList.remove('active');
        });
        clickedPill.classList.add('active');
        let tabId = clickedPill.getAttribute('id');
        await activePillContent(tabId);
    });
});

async function activePillContent(tabId) {
    pillsContent.forEach((clickedPillContent) => {
        clickedPillContent.classList.contains(tabId) ?
            clickedPillContent.classList.add('active') :
            clickedPillContent.classList.remove('active');
    })
}

async function getMyUser() {
    let res = await fetch('/api/auth');
    let resUser = await res.json();
    userNavbarDetails(resUser);
}

window.addEventListener('DOMContentLoaded', getMyUser);

function userNavbarDetails(resUser) {
    let userList = document.getElementById('myUserDetails');
    let roles = ''
    for (let role of resUser.roles) {
        roles += role + ' '
    }
    userList.insertAdjacentHTML('beforeend', `
        <b> ${resUser.username} </b> with roles: <a>${roles} </a>`);
}

function loadTableData(listAllUser) {
    console.log(listAllUser)
    let tableBody = document.getElementById('tbody');
    let dataHtml = '';
    for (let user of listAllUser) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(" " + role)
        }
        dataHtml +=
            `<tr>
    <td>${user.id}</td>
    <td>${user.username}</td>
    <td>${user.lastname}</td>
    <td>${user.email}</td>
    <td>${user.age}</td>
    <td>${roles}</td>
    <td>
        <button class="btn blue-background" data-bs-toogle="modal"
        data-bs-target="#editModal"
        onclick="editModalData(${user.id})">Edit</button>
    </td>
        <td>
        <button class="btn btn-danger" data-bs-toogle="modal"
        data-bs-target="#deleteModal"
        onclick="deleteModalData(${user.id})">Delete</button>
    </td>
</tr>`
    }
    tableBody.innerHTML = dataHtml;
}

getAdminPage();


async function loadUserTable() {
    let tableBody = document.getElementById('tableUser');
    let page = await fetch("/api/auth");
    let currentUser;
    if (page.ok) {
        currentUser = await page.json();
    } else {
        alert(`Error, ${page.status}`)
    }
    let dataHtml = '';
    let roles = [];
    for (let role of currentUser.roles) {
        roles.push(" " + role)
    }
    dataHtml +=
        `<tr>
    <td>${currentUser.id}</td>
    <td>${currentUser.username}</td>
    <td>${currentUser.lastname}</td>
    <td>${currentUser.email}</td>
    <td>${currentUser.age}</td>
    <td>${roles}</td>
</tr>`
    tableBody.innerHTML = dataHtml;
}

const tabs = document.querySelectorAll('.taba');
const tabsContent = document.querySelectorAll('.tabaContent');
tabs.forEach((clickedTab) => {
    clickedTab.addEventListener('click', async () => {
        tabs.forEach((tab) => {
            tab.classList.remove('active');
        });
        clickedTab.classList.add('active');
        let tabaId = clickedTab.getAttribute('id');
        await activeTabContent(tabaId);
    });
});

async function activeTabContent(tabaId) {
    tabsContent.forEach((clickedTabContent) => {
        clickedTabContent.classList.contains(tabaId) ?
            clickedTabContent.classList.add('active') :
            clickedTabContent.classList.remove('active');
    })
}

const form_new = document.getElementById('formForNewUser');
const usernameError = document.getElementById('usernameError');
const lastnameError = document.getElementById('lastnameError');
const passwordError = document.getElementById('passwordError');
const ageError = document.getElementById('ageError');
const emailError = document.getElementById('emailError');


async function newUser() {
}

form_new.addEventListener('submit', addNewUser);

async function addNewUser(event) {


    let urlCreate = 'http://localhost:8080/api/create/';
    event.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < form_new.roleSelect.options.length; i++) {
        if (form_new.roleSelect.options[i].selected) {
            listOfRole.push({
                id: form_new.roleSelect.options[i].value,
                role: form_new.roleSelect.options[i].text
            });
        }
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            username: form_new.username.value,
            lastname: form_new.lastname.value,
            email: form_new.email.value,
            age: form_new.age.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }
    const response = await fetch(urlCreate, method);
    if (response.ok) {
        form_new.reset();
        getAdminPage();
        activeTabContent('home-tab');
        let activateTab = document.getElementById('home-tab');
        activateTab.classList.add('active');
        let deactivateTab = document.getElementById('profile-tab');
        deactivateTab.classList.remove('active');
    } else {
        const errorData = await response.json();
        console.log(errorData)
        usernameError.innerHTML = '';
        lastnameError.innerHTML = '';
        ageError.innerHTML = '';
        passwordError.innerHTML = '';
        emailError.innerHTML = '';
        if (errorData && errorData.length > 0) {
            errorData.forEach((errorMessage) => {
                if (errorMessage.includes('username')) {
                    const errorMessageElement = document.createElement('p');
                    errorMessageElement.textContent = errorMessage;
                    usernameError.appendChild(errorMessageElement);
                } else if (errorMessage.includes('lastname')) {
                    const errorMessageElement = document.createElement('p');
                    errorMessageElement.textContent = errorMessage;
                    lastnameError.appendChild(errorMessageElement);
                } else if (errorMessage.includes('email')) {
                    const errorMessageElement = document.createElement('p');
                    errorMessageElement.textContent = errorMessage;
                    emailError.appendChild(errorMessageElement);
                } else if (errorMessage.includes('age')) {
                    const errorMessageElement = document.createElement('p');
                    errorMessageElement.textContent = errorMessage;
                    ageError.appendChild(errorMessageElement);
                } else if (errorMessage.includes('password')) {
                    const errorMessageElement = document.createElement('p');
                    errorMessageElement.textContent = errorMessage;
                    passwordError.appendChild(errorMessageElement);
                }
            });
        }
    }
}

form_new.addEventListener('submit', addNewUser);

const form_ed = document.getElementById('formForEditing');
const id_ed = document.getElementById('id_ed');
const username_ed = document.getElementById('username_ed');
const lastname_ed = document.getElementById('lastname_ed');
const email_ed = document.getElementById('email_ed');
const age_ed = document.getElementById('age_ed');
const password_ed = document.getElementById('password_ed');


async function editModalData(id) {
    $('#editModal').modal('show');
    const urlDataEd = 'http://localhost:8080/api/users/' + id;
    let usersPageEd = await fetch(urlDataEd);
    if (usersPageEd.ok) {
        await usersPageEd.json().then(user => {
            id_ed.value = `${user.id}`;
            username_ed.value = `${user.username}`;
            lastname_ed.value = `${user.lastname}`;
            email_ed.value = `${user.email}`;
            age_ed.value = `${user.age}`;
            password_ed.value = `${user.password}`;

            const selectedRoles = user.roles;
            const rolesSelect = document.getElementById('rolesForEditing');
            // console.log(user.roles);
            // console.log(rolesSelect.options[0].id);
            for (let i = 0; i < rolesSelect.options.length; i++) {
                rolesSelect.options[i].selected = selectedRoles.includes(rolesSelect.options[i].text);
            }
        })
    } else {
        alert(`Error, ${usersPageEd.status}`)
    }
}

async function editUser() {
    let urlEdit = 'http://localhost:8080/api/update/' + id_ed.value;

    let listOfRole = [];
    for (let i = 0; i < form_ed.rolesForEditing.options.length; i++) {
        if (form_ed.rolesForEditing.options[i].selected) {
            listOfRole.push(
                form_ed.rolesForEditing.options[i].text
            );
        }
        console.log(listOfRole)
    }
    let method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            id: id_ed.value,
            username: username_ed.value,
            lastname: lastname_ed.value,
            email: email_ed.value,
            age: age_ed.value,
            password: password_ed.value,
            roles: listOfRole
        })
    }
    await fetch(urlEdit, method).then((async () => {
        $('#editCloseBtn').click();
        await getAdminPage();
    }))
}

const form_del = document.getElementById('formForDeleting');
const id_del = document.getElementById('id_del');
const username_del = document.getElementById('username_del');
const lastname_del = document.getElementById('lastname_del');
const email_del = document.getElementById('email_del');
const age_del = document.getElementById('age_del');
const password_del = document.getElementById('password_del');


async function deleteModalData(id) {
    $('#deleteModal').modal('show');
    const urlForDel = 'http://localhost:8080/api/users/' + id;
    let usersPageDel = await fetch(urlForDel);
    if (usersPageDel.ok) {
        await usersPageDel.json().then(user => {
            id_del.value = `${user.id}`;
            username_del.value = `${user.username}`;
            lastname_del.value = `${user.lastname}`;
            email_del.value = `${user.email}`;
            age_del.value = `${user.age}`;
            password_del.value = `${user.password}`;
        })
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}

async function deleteUser() {
    let urlDel = 'http://localhost:8080/api/delete/' + id_del.value;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: form_new.username.value,
            lastname: form_new.lastname.value,
            email: form_new.email.value,
            age: form_new.age.value,
            password: form_new.password.value,
        })
    }
    await fetch(urlDel, method).then(() => {
        $('#deleteCloseBtn').click();
        getAdminPage();
    })
}



