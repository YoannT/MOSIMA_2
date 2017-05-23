use_module(library(jpl)).

%a fish is free or caught
%a fisherman is fishing, bredouille or victorious

%initial state


%a interessante(S) :-
%a     visible(X),
%a     inRange(X),
%a     domine(X,Y).
    
%a inRange(X) :-
%a     %jpl_call('java.lang.System',getProperty,['user.dir'],F),
%a 	%write(F)
%a     jpl_call('sma.actionsBehaviours.NotSoLuckyLukeBehaviour',isNear,[V],R).

shoot(V) :-
    %jpl_call('java.lang.System',getProperty,['user.dir'],F),
	%write(F).
	jpl_call('sma.actionsBehaviours.NotSoLuckyLukeBehaviour',test,[], @(void)),
	jpl_call('sma.actionsBehaviours.NotSoLuckyLukeBehaviour',isNear,[V],R),
	jpl_is_true(R).
	%a interessante(S)
	
