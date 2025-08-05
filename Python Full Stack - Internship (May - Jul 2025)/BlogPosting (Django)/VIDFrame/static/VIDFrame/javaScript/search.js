function filterUserCards(allUserCards, searchInputVal) {
    const searchTerm = searchInputVal.toLowerCase(); // Get input value and convert to lowercase

    allUserCards.each(function() {
        const card = $(this);
        const usernameSpan = card.find('.username');

        if (usernameSpan.length > 0) {
            const username = usernameSpan.data('username').toLowerCase(); 

            // Check if the username *contains* the search term (partial match)
            if (username.includes(searchTerm)) {
                card.show();
            } else {
                card.hide();
            }
        }
    });
}

function filterComments(allComments, searchInputVal){
    const searchTerm = searchInputVal.toLowerCase()

    allComments.each(function(){
        const comment = $(this);
        const postedByUserSpan = comment.find('.posted_by_user')

        if (postedByUserSpan.length > 0) {
            const postedByUsername = postedByUserSpan.data('by_user').toLowerCase();

            if (postedByUsername.includes(searchTerm)){
                comment.show();
            } else {
                comment.hide();
            }
        }
    });
}

function filterMediaName(allMedias, searchInputVal){
    const searchTerm = searchInputVal.toLowerCase();

    allMedias.each(function(){
        const media = $(this);
        const mediaNameSpan = media.find('.media_name')

        if (mediaNameSpan.length > 0){
            const mediaName = mediaNameSpan.data('media_name');

            if (mediaName.includes(searchTerm)){
                media.show();
            } else {
                media.hide();
            }
        }
    });
}