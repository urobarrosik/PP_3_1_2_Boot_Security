$(document).ready(function () {
    loadCurrentUser();
    loadRoles();
    loadUsers();

    $('#addUserForm').submit(function (event) {
        event.preventDefault();

        const data = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            age: $('#age').val(),
            username: $('#username').val(),
            password: $('#password').val(),
            roles: $('#roles').find(':selected').map(function () {
                return {
                    id: $(this).val(),
                    name: $(this).text()
                };
            }).get()
        };

        $.ajax({
            url: '/rest/rest_users',
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(data),
            contentType: 'application/json',
            success: function () {
                loadUsers();
                $('#home-tab').tab('show');
                $('#addUserForm')[0].reset();
                alert('Пользователь успешно добавлен!');
            },
            error: function () {
                alert('Произошла ошибка при добавлении. Попробуйте еще раз.');
            }
        });
    });

    $('#roles').empty();
    $('#rolesModal').empty();
    $('#rolesModalDelete').empty();
});

function loadCurrentUser() {
    $.ajax({
        url: '/rest/user/current',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#admin-username').text(data.username);
            let roles = data.roles.map(role => role.name).join(', ');
            $('#admin-roles').text(roles);
        }
    });
}

function loadRoles() {
    $.ajax({
        url: '/rest/roles',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            renderRoleButtons(data);
            renderRolesSelect(data);
        }
    });
}

function loadUsers() {
    $.ajax({
        url: '/rest/rest_users',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            renderUsers(data);
        }
    });
}

function renderRoleButtons(roles) {
    $('#sidebar-roles').empty();
    roles.forEach(role => {
        let button = $('<button type="button" class="btn btn-primary role-button">' + role.name + '</button>');
        button.attr('data-link', role.link);
        $('#sidebar-roles').append(button);
    });
}

function renderUsers(users) {
    $('#users-table tbody').empty();
    users.forEach(user => {
        let row = $('<tr></tr>');
        row.append('<td>' + user.id + '</td>');
        row.append('<td>' + user.firstName + '</td>');
        row.append('<td>' + user.lastName + '</td>');
        row.append('<td>' + user.age + '</td>');
        row.append('<td>' + user.username + '</td>');
        row.append('<td>' + user.roles.map(role => role.name).join(', ') + '</td>');
        row.append('<td><button type="button" class="btn btn-info edit-button" data-toggle="modal" data-target="#editUserModal" data-user-id="' + user.id + '">Edit</button></td>');
        row.append('<td><button type="button" class="btn btn-info btn-danger" data-toggle="modal" data-target="#deleteUserModal" data-user-id="' + user.id + '">Delete</button></td>');
        $('#users-table tbody').append(row);
    });
}

function renderRolesSelect(roles) {
    let selectElements = $('#roles, #rolesModal, #rolesModalDelete');
    selectElements.empty();
    roles.forEach(role => {
        let option = $('<option value="' + role.id + '">' + role.name + '</option>');
        selectElements.append(option);
    });
}