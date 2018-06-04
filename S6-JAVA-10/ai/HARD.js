var act = function(buttons, playerMove) {
    var size = 5;
    var possibleMoves = [];
    for (var i = 0; i < buttons.length; i++)
    {
        
        if(buttons[i] == (playerMove - 1)){            
            possibleMoves.push(buttons[i])
        }
        if(buttons[i] == (playerMove + 1)){
            possibleMoves.push(buttons[i])
        }
        if(buttons[i] == (playerMove - size)){
            possibleMoves.push(buttons[i])
        }
        if(buttons[i] == (playerMove + size)){
            possibleMoves.push(buttons[i])
        }
    }
    if (possibleMoves.length > 0){
        var index = Math.floor((Math.random() * possibleMoves.length));
        return possibleMoves[index];
    }
    var index = Math.floor((Math.random() * buttons.length));
    return buttons[index];
}