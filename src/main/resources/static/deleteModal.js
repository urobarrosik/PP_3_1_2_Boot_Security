$('#deleteUserModal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let userId = button.data('user-id');

    $.ajax({
        url: '/rest/rest_users/' + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#userIdDelete').val(data.id);
            $('#idDelete').val(data.id);
            $('#firstNameModalDelete').val(data.firstName);
            $('#lastNameModalDelete').val(data.lastName);
            $('#ageModalDelete').val(data.age);
            $('#usernameModalDelete').val(data.username);

            let rolesSelect = $('#rolesModalDelete');
            rolesSelect.empty();
            $.each(data.roles, function (index, role) {
                rolesSelect.append('<option value="' + role.id + '" selected>' + role.name + '</option>');
            });
        }
    });
});

$('#deleteUserForm').submit(function (event) {
    event.preventDefault();
    let userIdDelete = $('#userIdDelete').val();

    $.ajax({
        url: '/rest/rest_users/' + userIdDelete,
        type: 'DELETE',
        success: function () {
            loadUsers();
            $('#deleteUserModal').modal('hide');
            alert('Пользователь успешно удален!');
        },
        error: function () {
            alert('Ошибка удаления');
        }
    });
});
