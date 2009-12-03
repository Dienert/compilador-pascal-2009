{
  er-sem24.s
      
  parametro por referencia deve ser variavel
  }
program erro24;
  function f : integer;
    begin
    f:= 0
    end

  procedure p( a : integer; var b : integer);
    begin
    end


begin
p(f, f)
end.
