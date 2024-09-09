$('.btn-danger').click(function () {
    let userId = $(this).data('user-id');

    $.ajax({
        url: '/edit?id=' + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            $('#deleteUserModal').find('input[name="id"]').val(data.id);
            $('#deleteUserModal').find('input[name="firstName"]').val(data.firstName);
            $('#deleteUserModal').find('input[name="lastName"]').val(data.lastName);
            $('#deleteUserModal').find('input[name="age"]').val(data.age);
            $('#deleteUserModal').find('input[name="username"]').val(data.username);


            let rolesSelect = $('#deleteUserModal').find('select[name="roles"]');
            rolesSelect.empty();
            $.each(data.roles, function (index, role) {
                rolesSelect.append('<option value="' + role.id + '" selected>' + role.name + '</option>');
            });
        }
    });
});