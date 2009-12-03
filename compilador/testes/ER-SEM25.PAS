{
  er-sem25.s
      
  Variaveis locais e parametros estao no mesmo escopo
  }
program erro25;      
    procedure m( a : integer; var b : integer);
        var a, b : integer;
      begin
      end

  var c : integer;
begin
m(c, c)
end.
