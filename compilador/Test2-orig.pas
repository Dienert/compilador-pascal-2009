program Test2;
var
   X, A, B : integer;
begin
   A := 5;
   B := 10;
   if (A > B) then
   begin
      X := A;
      A := B;
      B := X;
   end
end.

{acrescentar s�mbolo n�o permitido}