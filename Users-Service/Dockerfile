FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS base
WORKDIR /app
EXPOSE 80

FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /src
COPY ["Users-Service.csproj", "."]
RUN dotnet restore "Users-Service.csproj"
COPY . .
WORKDIR "/src"
RUN dotnet build "Users-Service.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "Users-Service.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Users-Service.dll"]
