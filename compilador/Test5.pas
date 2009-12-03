program Test5;
var
   A, B, R, I : integer;
begin
   for J:= 1 to 10 do
   begin
   for I := 1 to 5 do
   begin
      A := A * A;
      B := B * A;
      R := A + B;
   end
   end
end.