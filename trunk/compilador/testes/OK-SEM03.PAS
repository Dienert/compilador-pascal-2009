{
  ok-sem03.s

  Testa se o compilador aceita variavel local e global com mesmo
  nome.
  }
program ok03;
var n : integer;

    procedure set( pn : integer );
        var n : integer;
      begin
      n:= pn
      end
    function put( n : integer ) : integer;
      begin
      put:= n
      end

begin
set(4);
writeln( put(5) )
end.
