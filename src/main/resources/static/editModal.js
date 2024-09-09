$('.edit-button').click(function () {
    const userId = $(this).data('userId');

    $.ajax({
        url: '/edit?id=' + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            $('#editUserModal').find('input[name="id"]').val(data.id);
            $('#editUserModal').find('input[name="firstName"]').val(data.firstName);
            $('#editUserModal').find('input[name="lastName"]').val(data.lastName);
            $('#editUserModal').find('input[name="age"]').val(data.age);
            $('#editUserModal').find('input[name="username"]').val(data.username);


            let rolesSelect = $('#editUserModal').find('select[name="rolesModal"]');
            rolesSelect.empty();
            $.each(data.roles, function (index, role) {
                rolesSelect.append('<option value="' + role.id + '" selected>' + role.name + '</option>');
            });
        }
    });
});