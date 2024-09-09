// Edit button click handler
$('#editUserModal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget); // Получаем кнопку, которая открыла модальное окно
    let userId = button.data('user-id'); // Получаем ID пользователя

    $.ajax({
        url: '/rest/rest_users/' + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#userId').val(data.id);
            $('#id').val(data.id);
            $('#firstNameModal').val(data.firstName);
            $('#lastNameModal').val(data.lastName);
            $('#ageModal').val(data.age);
            $('#usernameModal').val(data.username);

            loadRoles();
        }
    });
});

$('#editUserForm').submit(function(event) {
    event.preventDefault();

    const data = {
        id: $('#userId').val(),
        firstName: $('#firstNameModal').val(),
        lastName: $('#lastNameModal').val(),
        age: parseInt($('#ageModal').val(), 10),
        username: $('#usernameModal').val(),
        password: $('#passwordModal').val(),
        roles: $('#rolesModal').find(':selected').map(function() {
            return {
                id: parseInt($(this).val(), 10),
                name: $(this).text()
            };
        }).get()
    };

    $.ajax({
        url: '/rest/rest_users',
        type: 'PUT',
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function() {
            loadUsers();
            $('#editUserModal').modal('hide');
            alert('Пользователь успешно изменён!');
        },
        error: function() {
            alert('Произошла ошибка при изменении данных. Попробуйте еще раз.');
        }
    });
});