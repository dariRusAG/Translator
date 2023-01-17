var x1,y1,x2,y2,x3,y3,a,b,c,p,s:real;
begin
readln( x1,y1,x2,y2,x3,y3 );
c:=sqrt(sqr(y1-y2)+sqr(x1-x2));
a:=sqrt(sqr(y2-y3)+sqr(x2-x3));
b:=sqrt(sqr(y1-y3)+sqr(x1-x3));
p:=(a+b+c)/2;
s:=p*sqrt((p-a)*(p-b)*(p-c));
end.
