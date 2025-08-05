// Create a custom validation methods here in this file

//"not entirely numeric"
function addNotNumericOnly(){
    $.validator.addMethod("notNumericOnly", function(value, element) {
        return this.optional(element) || /[^0-9]/.test(value);
    }, "Your password can't be entirely numeric.");
}

function addDiffPassword(oldPasswordEle){
    $.validator.addMethod("notEqualToOld", function(value, element) {
        const oldPassword = $(oldPasswordEle).val();
        if (this.optional(element) || oldPassword === "") {
            return true;
        }
        return value !== oldPassword;
    }, "New password cannot be the same as your old password.");
}

function loginFormValidation(loginForm){
    addNotNumericOnly();
    $(loginForm).validate({
        rules: {
            username: {
                required: true,
                minlength: 3 
            },
            password: {
                required: true,
                minlength: 8,
                notNumericOnly: true
            }
        },
        messages: {
            password: {
                required: "Please Enter your password.",
                minlength: "Your password must be atleast 8 characters."
            },
        },
        errorPlacement: function(error, element){
            $(error).insertAfter($(element).parent()).addClass('text-danger');
        },
        highlight: function(element){
            $(element).addClass('text-danger');
        },
        unhighlight: function(element) {
            $(element).removeClass('text-danger');
        },
        success: function(label, element) {
            $(element).toggleClass('text-danger text-success');
            $(label).remove();
        }
    });
}

function pwdResetFormValidation(formEle, pwd1Ele){
    addNotNumericOnly();
    $(formEle).validate({
        rules: {
            // Rule for new_password1 field (Django form field name)
            new_password1: {
                required: true,
                minlength: 8,
                notNumericOnly: true // Apply our custom method
            },
            // Rule for new_password2 field
            new_password2: {
                required: true,
                equalTo: pwd1Ele // Compares to the ID of the first password field
            }
        },
        messages: {
            new_password1: {
                required: "Please enter your new password.",
                minlength: "Your password must be at least 8 characters long.",
                // The message for notNumericOnly is defined in addMethod
            },
            new_password2: {
                required: "Please re-enter your new password.",
                equalTo: "Passwords do not match."
            }
        },
        errorPlacement: function(error, element) {
            $(error).insertAfter($(element).parent('.input-group')).addClass('text-danger');
        },
        highlight: function(element) {
            $(element).addClass('text-danger'); 
        },
        unhighlight: function(element) {
            $(element).removeClass('text-danger');
        },
        success: function(label, element) {
            $(element).toggleClass('text-danger text-success');
            $(label).remove();
        }
    });
}

function registerFormValidation(registerForm,passwordInputEle1){
    addNotNumericOnly();
    $(registerForm).validate({
        rules: {
            username: {
                required: true,
                minlength: 3 
            },
            first_name: {
                required: true,
            },
            last_name: {
                required: true,
            },
            email: {
                required: true,
                email: true
            },
            contact: {
                required: true,
                digits: true, 
                minlength: 10, 
                maxlength: 10
            },
            password1: {
                required: true,
                minlength: 8,
                notNumericOnly: true 
            },
            password2: {
                required: function(element) {
                    return $(passwordInputEle1).val().length > 0;
                },
                equalTo: passwordInputEle1 
            },
            profile_pic: {
                extension: "png|jpeg|jpg|gif"
            }
        },
        messages: {
            username: {
                required: "Please enter a username.",
                minlength: "Username must be at least 3 characters."
            },
            first_name: {
                required: "Please enter a firstname.",
            },
            last_name: {
                required: "Please enter a lastname.",
            },
            email: {
                required: "Please enter your email address.",
                email: "Please enter a valid email address."
            },
            contact: {
                required: "Please enter your contact number.",
                digits: "Please enter only digits for contact number.",
                minlength: "Contact number must be 10 digits.",
                maxlength: "Contact number must be 10 digits."
            },
            password1: {
                required: "Please enter your new password.",
                minlength: "Your new password must be at least 8 characters long.",
                notNumericOnly: "Your new password can't be entirely numeric."
            },
            password2: {
                required: "Please re-enter your new password.",
                equalTo: "New passwords do not match."
            },
            profile_pic: {
                extension: "Not a valid file. Valid: png|jpeg|jpg|gif"
            }
        },
        errorPlacement: function(error, element){
            $(error).insertAfter($(element).parent()).addClass('text-danger');
        },
        highlight: function(element){
            $(element).addClass('text-danger');
        },
        unhighlight: function(element) {
            $(element).removeClass('text-danger');
        },
        success: function(label, element) {
            $(element).toggleClass('text-danger text-success');
            $(label).remove();
        }
    });
}

function profileFormValidation(profileForm,oldPwdEle,newPwdEle1,newPwdEle2){
    console.log("profileFormValidation");
    addNotNumericOnly();
    addDiffPassword($(oldPwdEle));
    $(profileForm).validate({
        rules: {
            username: {
                required: true,
                minlength: 3 
            },
            first_name: {
                required: true,
            },
            last_name: {
                required: true,
            },
            email: {
                required: true,
                email: true
            },
            contact: {
                required: true,
                digits: true, 
                minlength: 10, 
                maxlength: 10
            },
            old_pwd: {
                required: function(element) {
                    return $(newPwdEle1).val().length > 0 || $(newPwdEle2).val().length > 0;
                }
            },
            new_pwd1: {
                required: function(element) {
                    return $(oldPwdEle).val().length > 0 || $(newPwdEle2).val().length > 0;
                },
                minlength: 8,
                notNumericOnly: true,
                notEqualToOld: true
            },
            new_pwd2: {
                required: function(element) {
                    return $(newPwdEle1).val().length > 0;
                },
                equalTo: newPwdEle1 
            },
            profile_pic: {
                extension: "png|jpeg|jpg|gif"
            }
        },
        messages: {
            username: {
                required: "Please enter a username.",
                minlength: "Username must be at least 3 characters."
            },
            first_name: {
                required: "Please enter a firstname.",
            },
            last_name: {
                required: "Please enter a lastname.",
            },
            email: {
                required: "Please enter your email address.",
                email: "Please enter a valid email address."
            },
            contact: {
                required: "Please enter your contact number.",
                digits: "Please enter only digits for contact number.",
                minlength: "Contact number must be 10 digits.",
                maxlength: "Contact number must be 10 digits."
            },
            old_pwd: {
                required: "Please enter your old password to change it."
            },
            new_pwd1: {
                required: "Please enter your new password.",
                minlength: "Your new password must be at least 8 characters long.",
                notNumericOnly: "Your new password can't be entirely numeric."
            },
            new_pwd2: {
                required: "Please re-enter your new password.",
                equalTo: "New passwords do not match."
            },
            profile_pic: {
                extension: "Not a valid file. Valid: png|jpeg|jpg|gif"
            }
        },
        errorPlacement: function(error, element){
            $(error).insertAfter($(element).parent()).addClass('text-danger');
        },
        highlight: function(element){
            $(element).addClass('text-danger');
        },
        unhighlight: function(element) {
            $(element).removeClass('text-danger');
        },
        success: function(label, element) {
            $(element).toggleClass('text-danger text-success');
            $(label).remove();
        },
        submitHandler: function(form){
            processProfileForm(form);
        }
    });
    function processProfileForm(profileForm){
        $(profileForm).find('#submitBtn').on('click', function(event) {
            event.preventDefault();

            const formData = new FormData();

            $('form input[name]:not(:disabled)').each(function() {
                const $input = $(this);
                const inputName = $input.attr('name');
                let inputValue = $input.val();

                if (!($input.attr('type') === 'password')){
                    if ($input.attr('type') === 'file') {
                        if ($input[0].files.length > 0) { 
                            inputValue = $input[0].files[0];
                        }
                    } 
                    if (Boolean(inputValue)){
                        formData.append(inputName, inputValue);
                    }
                }
            });

            if (Boolean($(old_pwdEle).val())) {
                const old_pwd_val = old_pwdEle.val();
                const pwd1_val = new_pwd1Ele.val();
                const pwd2_val = new_pwd2Ele.val();
                if (Boolean($(new_pwd1Ele).val())){
                    if (pwd1_val == pwd2_val){
                        formData.append($(old_pwdEle).attr('name'), old_pwd_val);
                        formData.append($(new_pwd1Ele).attr('name'), pwd1_val);
                        formData.append($(new_pwd2Ele).attr('name'), pwd2_val);
                    }
                }
            }
            $.ajax({
                url: "{% url 'updateProfile' %}",
                type: 'POST',
                data: formData,
                headers: {
                    'X-CSRFToken': '{{csrf_token}}', // Add the token here as a header
                    'X-Requested-With': 'XMLHttpRequest', // jQuery usually adds this by default
                },
                processData: false, // Critical: tells jQuery not to process the data string
                contentType: false, // Critical: tells jQuery not to set the Content-Type header
                dataType: 'json', // Expect JSON response from Django
                success: function(response) {
                    if (response.status === 'success') {
                        alert(response.message);
                        if(Boolean(formData.get('new_pwd1'))){
                            location.assign("{% url 'logout' %}")
                        }
                    } else {
                        alert(response.message);
                    }
                },
                error: function(status, error) {
                    alert(status.responseJSON.message);
                    console.error('AJAX Error:', status);
                }
            }); 
        });
    }
}